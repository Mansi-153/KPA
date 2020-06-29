package com.example.kamdhenupashuahar.Fragments;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kamdhenupashuahar.R;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class DetailsOfUdhaar extends Fragment {
    private static final String[][] DATA_TO_SHOW = { { "23/09/20", "5", "2", "1","1","0","0","0","5678","2000","36784" }};
    private static final String[] TABLE_HEADERS = { "Date", "Bhusa", "Cho", "Masoor","Arhar","Kutti","AK","SK","Total","Paid","Balance" };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root  = inflater.inflate(R.layout.fragment_details_of_udhaar, container, false);
        // Inflate the layout for this fragment
        TableView<String[]> tableView = (TableView<String[]>) root.findViewById(R.id.tableView);
        TableColumnWeightModel columnModel = new TableColumnWeightModel(11);
        columnModel.setColumnWeight(0, 2);
        columnModel.setColumnWeight(8, 2);
        columnModel.setColumnWeight(9, 2);
        columnModel.setColumnWeight(10, 2);
        tableView.setColumnModel(columnModel);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(getActivity(), TABLE_HEADERS));
        tableView.setDataAdapter(new SimpleTableDataAdapter(getActivity(), DATA_TO_SHOW));
        return root;
    }
}
