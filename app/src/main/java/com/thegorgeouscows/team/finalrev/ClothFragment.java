package com.thegorgeouscows.team.finalrev;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClothFragment extends Fragment {
    private RecyclerView clothes_list_view;
    List<Clothes> clothes_list;
    private FirebaseFirestore db;
    private ClothRecyclerAdapter clothRecyclerAdapter;


    public ClothFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("my","Got to CLOTHES FRAGMENT");
        View view = inflater.inflate(R.layout.fragment_cloth,container,false);
        clothes_list_view = (RecyclerView) view.findViewById(R.id.cloth_list_view);
        clothes_list = new ArrayList<>();
        clothRecyclerAdapter = new ClothRecyclerAdapter(clothes_list);

        clothes_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        clothes_list_view.setAdapter(clothRecyclerAdapter);
        clothes_list_view.setHasFixedSize(true);

        db = FirebaseFirestore.getInstance();
        db.collection("ClothPosts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (final QueryDocumentSnapshot document: task.getResult()){
                        //Log.i("my",document.getId()+ "=>"+document.getData());
                        DocumentReference docRef = db.collection("ClothPosts").document(document.getId());
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Clothes cloth = documentSnapshot.toObject(Clothes.class);
                                clothes_list.add(cloth);
                                Log.i("my","passed cloth as object and added to feed");
                                //System.out.println(Arrays.toString(posts_list.toArray()));
                                clothRecyclerAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                }
                else {
                    Log.d("my", "Error getting documents: ", task.getException());
                }

            }
        });

        return view;
    }

}
