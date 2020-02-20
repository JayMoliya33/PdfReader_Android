package com.example.pdfreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PDFAdapter extends ArrayAdapter<File> {

    Context context;
    ViewHolder viweholder;
    ArrayList<File> filepdf;

    public PDFAdapter(Context context, ArrayList<File> filepdf)
    {
        super(context, R.layout.pdf_adapter, filepdf);
        this.context=context;
        this.filepdf=filepdf;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if(filepdf.size()>0)
        {
            return filepdf.size();
        }
        else {
            return 1;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pdf_adapter, parent, false);
            viweholder = new ViewHolder();

            viweholder.mfilename = (TextView) convertView.findViewById(R.id.filename);
            convertView.setTag(viweholder);

        } else
            {
            viweholder = (ViewHolder) convertView.getTag();
        }

        viweholder.mfilename.setText(filepdf.get(position).getName());
        return convertView;
    }

    public class ViewHolder{
        TextView mfilename;
    }
}
