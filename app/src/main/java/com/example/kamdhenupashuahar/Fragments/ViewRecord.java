package com.example.kamdhenupashuahar.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import com.example.kamdhenupashuahar.R;
import com.example.kamdhenupashuahar.util.BundleClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

public class ViewRecord extends Fragment {
    TextView bhusa, cho, masoor, arhar, kutti, alsi, sarso, name1, balance;
    FirebaseFirestore db;
    ArrayList<String> arrayList1 = new ArrayList<>();
    ListView listView;
    CardView card1;
    Button detais;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.view_record, container, false);
        bhusa = root.findViewById(R.id.bhusa);
        cho = root.findViewById(R.id.cho);
        masoor = root.findViewById(R.id.masoor);
        arhar = root.findViewById(R.id.arhar);
        kutti = root.findViewById(R.id.kutti);
        alsi = root.findViewById(R.id.alsi);
        sarso = root.findViewById(R.id.sarso);
        listView = root.findViewById(R.id.list);
        card1 = root.findViewById(R.id.card1);
        name1 = root.findViewById(R.id.name1);
        balance = root.findViewById(R.id.balance);
        detais = root.findViewById(R.id.details);
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList1);
        listView.setAdapter(arrayAdapter);

        db = FirebaseFirestore.getInstance();

        final SearchView searchView = (SearchView) root.findViewById(R.id.searchView2);
        searchView.setQueryHint("Search Customer here");
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayList1.clear();
                final List<String> list = new ArrayList<>();
                final String charText = newText.toLowerCase(Locale.getDefault());
                db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Udhaar").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> list = new ArrayList<>();
                            list.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getId());
                            }
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).contains(charText)) {
                                    arrayList1.add(list.get(i));
                                }
                            }
                            Log.d("mansiiiiii", arrayList1.toString());
                            listView.setAdapter(arrayAdapter);

                        } else {
                            Log.d("mansiiii", "Error getting documents: ", task.getException());
                        }
                    }
                });
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) listView.getItemAtPosition(position);
                arrayList1.clear();
                card1.setVisibility(View.VISIBLE);
                name1.setText(clickedItem);
                read(clickedItem);
            }
        });
        return root;
    }

    public void read(final String s1) {
        DocumentReference docRef = db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Udhaar").document(s1);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    long b = 0, c = 0, a = 0, m = 0, k = 0, al = 0, s = 0;
                    if (document.exists()) {
                        balance.setText(Double.toString(document.getDouble("TotalBal")));
                        ArrayList<Map<String, Object>> map = new ArrayList<>();
                        map = (ArrayList<Map<String, Object>>) document.get("Array");
                        final String[][] strings = new String[map.size()][10];
                        for (int i = 0; i < map.size(); i++) {
                            strings[i][0]= String.valueOf(map.get(i).get("Date"));
                            Log.d("mansiiiiiiiiiii",map.get(i).get("ItemInfo").toString());
                            ArrayList<Long> qty = (ArrayList<Long>) map.get(i).get("ItemInfo");
                            b += qty.get(1);
                            c += qty.get(3);
                            a += qty.get(5);
                            m += qty.get(7);
                            k += qty.get(9);
                            al += qty.get(11);
                            s += qty.get(13);
                            strings[i][1]= String.valueOf(b);
                            strings[i][2]= String.valueOf(c);
                            strings[i][3]= String.valueOf(a);
                            strings[i][4]= String.valueOf(m);
                            strings[i][5]= String.valueOf(k);
                            strings[i][6]= String.valueOf(al);
                            strings[i][7]= String.valueOf(s);
                            strings[i][8] = String.valueOf(map.get(i).get("TotalAmount"));
                            strings[i][9] = String.valueOf(map.get(i).get("TotalPaid"));
                        }
                        final BundleClass bundleClass = new BundleClass(getActivity());
                        bundleClass.setRes(strings);
                        bundleClass.setName(s1);
                        bhusa.setText(String.valueOf(b));
                        cho.setText(String.valueOf(c));
                        arhar.setText(String.valueOf(a));
                        masoor.setText(String.valueOf(m));
                        kutti.setText(String.valueOf(k));
                        alsi.setText(String.valueOf(al));
                        sarso.setText(String.valueOf(s));
                        detais.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Fragment frag = new DetailsOfUdhaar();
                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("obj",bundleClass);
                                frag.setArguments(bundle);
                                ft.replace(R.id.fragment_place, frag);
                                ft.commit();
                            }
                        });

                    } else {
                        Log.d("", "No such document");
                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });
    }
}