package ru.saransklife.client.reference;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.saransklife.R;
import ru.saransklife.dao.ReferenceCategory;

/**
 * Created by asavinova on 06/02/15.
 */
public class ReferenceCategoryAdapter extends RecyclerView.Adapter<ReferenceCategoryAdapter.ViewHolder> {

	private List<ReferenceCategory> categories;

	public ReferenceCategoryAdapter(List<ReferenceCategory> categories) {
		this.categories = categories;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.ref_categories_recycle_view_item, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int i) {
		holder.setCategory(categories.get(i));
	}

	@Override
	public int getItemCount() {
		return categories.size();
	}

	public void updateCategories(List<ReferenceCategory> categories) {
		this.categories = categories;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		public TextView name;
		public ImageView icon;

		private ReferenceCategory category;

		public ViewHolder(View view) {
			super(view);
			view.setOnClickListener(this);
			name = (TextView) view.findViewById(R.id.name);
			icon = (ImageView) view.findViewById(R.id.icon);
		}

		@Override
		public void onClick(View v) {
			ReferencesActivity_.intent(v.getContext()).slug(category.getSlug()).start();
		}

		public void setCategory(ReferenceCategory category) {
			this.category = category;
			name.setText(category.getName());
			icon.setImageResource(CategoryType.findTypeBySlug(category.getSlug()).getIcon());
		}
	}
}