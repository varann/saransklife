package ru.saransklife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.saransklife.api.model.ApiMenuItem;

/**
 * Created by asavinova on 17/10/14.
 */
public class SectionsAdapter extends ArrayAdapter<ApiMenuItem> {

	enum MenuIcon {
		MAIN(R.drawable.white_icon_home),
		PAGE(R.drawable.white_icon_city_about),
		PLACE(R.drawable.white_icon_place),
		EVENT(R.drawable.white_icon_event),
		REFERENCE(R.drawable.white_icon_reference_book),
		ABOUT(R.drawable.white_icon_about);

		private String module;
		private final int icon;

		MenuIcon(int icon) {
			this.module = name();
			this.icon = icon;
		}

		public int getIcon() {
			return icon;
		}

		public static MenuIcon findIconByModule(String module) {
			MenuIcon[] values = values();
			for (MenuIcon menuIcon : values) {
				if (menuIcon.module.equalsIgnoreCase(module)) {
					return menuIcon;
				}
			}
			throw new RuntimeException("Module " + module + " not found");
		}
	}

	private final LayoutInflater inflater;

	public SectionsAdapter(Context context, int resource, List<ApiMenuItem> menu) {
		super(context, resource);
		ApiMenuItem mainItem = new ApiMenuItem();
		mainItem.setId(0l);
		mainItem.setModule("main");
		mainItem.setName("Main");
		add(mainItem);
		addAll(menu);

		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;

		if (view == null) {
			view = inflater.inflate(R.layout.list_drawer_item, null);

			holder = new ViewHolder();
			holder.icon = (ImageView) view.findViewById(R.id.icon);
			holder.title = (TextView) view.findViewById(R.id.title);

			view.setTag(holder);
		}

		holder = (ViewHolder) view.getTag();
		ApiMenuItem item = getItem(position);
		holder.icon.setImageResource(MenuIcon.findIconByModule(item.getModule()).getIcon());
		holder.title.setText(item.getName());

		return view;
	}

	static class ViewHolder {
		ImageView icon;
		TextView title;
	}
}
