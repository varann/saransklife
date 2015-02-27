package ru.saransklife.client.reference;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.saransklife.R;
import ru.saransklife.dao.Reference;

/**
 * Created by asavinova on 06/02/15.
 */
public class ReferencesAdapter extends RecyclerView.Adapter<ReferencesAdapter.ViewHolder> {

	private List<Reference> references;

	public ReferencesAdapter(List<Reference> references) {
		this.references = references;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.references_recycle_view_item, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int i) {
		holder.setCategory(references.get(i));
	}

	@Override
	public int getItemCount() {
		if (references == null) return 0;
		return references.size();
	}

	public void updateReferences(List<Reference> references) {
		this.references = references;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		public TextView name;
		public TextView description;

		private Reference reference;

		public ViewHolder(View view) {
			super(view);
			view.setOnClickListener(this);
			name = (TextView) view.findViewById(R.id.name);
			description = (TextView) view.findViewById(R.id.description);
		}

		@Override
		public void onClick(View v) {
			ReferenceInfoActivity_.intent(v.getContext()).id(reference.getId()).start();
		}

		public void setCategory(Reference reference) {
			this.reference = reference;
			name.setText(reference.getName());
			description.setText(reference.getDescription());
		}
	}
}