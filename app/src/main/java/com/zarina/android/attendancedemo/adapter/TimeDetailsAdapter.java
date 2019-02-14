package com.zarina.android.attendancedemo.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zarina.android.attendancedemo.R;
import com.zarina.android.attendancedemo.model.timingscreen.TimeDetails;

import java.sql.Time;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeDetailsAdapter extends RecyclerView.Adapter<TimeDetailsAdapter.TimeDetailsViewHolder>{

    private List<TimeDetails> mListTimeDetails;
    private Context context;

    class TimeDetailsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_date)
        TextView textViewDate;
        @BindView(R.id.tv_timein)
        TextView textViewTimeIn;
        @BindView(R.id.tv_timeout)
        TextView textViewTimeOut;
        @BindView(R.id.relativelistlayout)
        RelativeLayout relativeLayout;

        TimeDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }


    public TimeDetailsAdapter(Context context) {
        this.context = context;
    }

    public void displayTimeList(List<TimeDetails> timeDetailsList, ProgressDialog progressDialog) {
        this.mListTimeDetails = timeDetailsList;
        notifyDataSetChanged();
        progressDialog.hide();

    }

    @NonNull
    @Override
    public TimeDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_time_detail,parent,false);

        return new TimeDetailsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final TimeDetailsViewHolder holder, final int listPosition) {

        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        LinearLayout.LayoutParams mLayoutParams =
                ((LinearLayout.LayoutParams)holder.relativeLayout.getLayoutParams());

        //for adjusting list item height based on screen length
        mLayoutParams.height=(int)(15 * height / 100);

        TimeDetails timeDetails = mListTimeDetails.get(listPosition);
        holder.textViewDate.setText(timeDetails.getDate());
        holder.textViewTimeIn.setText(timeDetails.getTimein());
        holder.textViewTimeOut.setText(timeDetails.getTimeout());

    }

    @Override
    public int getItemCount() {
        return mListTimeDetails == null ? 0 : mListTimeDetails.size();
    }


}
