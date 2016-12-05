package com.bibliogames.nygar.bibliogames.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.GraphicEntry;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphicFragment extends Fragment {

    @BindView(R.id.chart)
    PieChart chart;
    @BindView(R.id.textView_TotalGraphic)
    TextView tvTotal;

    @BindString(R.string.graphic_total)
    String graphicTotal;
    @BindColor(R.color.transparent)
    int chartBackgroundColor;
    @BindColor(R.color.white)
    int chartTextColor;

    private List<GraphicEntry> graphicEntries;
    private PieData data;

    public GraphicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_graphic, container, false);
        ButterKnife.bind(this,view);

        inicializateGraphic();
        return view;
    }

    private void inicializateGraphic(){
        //graphicEntries = testData();
        if(graphicEntries==null) {
            graphicEntries = new ArrayList<>();
        }
        addDataToGraphic(graphicEntries);
        chart.setDescription("");
        chart.setHoleColor(chartBackgroundColor);
        chart.setDrawCenterText(true);
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    private float calculateTotal(List<GraphicEntry> list){
        float total =0;
        for (GraphicEntry g: list) {
            total = total + g.getEntryData();
        }
        return total;
    }

    private void addDataToGraphic(List<GraphicEntry> list){
        tvTotal.setText(graphicTotal+" "+ calculateTotal(list)+ " €");

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (int i=0; i<list.size();i++) {
            // turn your data into Entry objects
            entries.add(new Entry(list.get(i).getEntryData(),i));
            labels.add(list.get(i).getEntryTittle());
        }

        PieDataSet dataSet = new PieDataSet(entries,""); // add entries to dataset
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(16);
        dataSet.setValueTextColor(chartTextColor);

        data = new PieData(labels,dataSet);
        chart.setData(data);
    }

    public void updateGraphic(List<GraphicEntry> list){
        graphicEntries=list;
        if(tvTotal!=null) {
            addDataToGraphic(list);
            chart.invalidate();
        }
    }

    /*private List<GraphicEntry> testData(){
        List<GraphicEntry> res = new ArrayList<>();
        res.add(new GraphicEntry("Play",150));
        res.add(new GraphicEntry("Nintendo",150));
        res.add(new GraphicEntry("Xbox",180));
        res.add(new GraphicEntry("Sega",10));

        return res;
    }*/

}
