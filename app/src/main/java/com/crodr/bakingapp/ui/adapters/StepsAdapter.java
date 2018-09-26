package com.crodr.bakingapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crodr.bakingapp.R;
import com.crodr.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {
    private List<Step> stepsList;
    private StepClickListener listener;

    public StepsAdapter(List<Step> steps, StepClickListener listener) {
        this.listener = listener;
        this.stepsList = steps;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);
        return new StepsAdapter.StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(stepsList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return (stepsList != null)? stepsList.size(): 0;
    }

    class StepViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_step)
        TextView txtStep;

        StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Step step, final StepClickListener listener) {
            txtStep.setText(step.getShortDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(step);
                }
            });
        }
    }

    public interface StepClickListener {
        void onClick(Step step);
    }
}
