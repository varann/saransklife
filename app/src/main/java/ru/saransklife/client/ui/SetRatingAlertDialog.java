package ru.saransklife.client.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import ru.saransklife.R;

/**
 * Created by asavinova on 06/02/15.
 */
public class SetRatingAlertDialog extends AlertDialog.Builder {

	public enum Rating {
		AWFULLY(1, R.string.rating_awfully),
		BAD(2, R.string.rating_bad),
		MEDIUM(3, R.string.rating_medium),
		GOOD(4, R.string.rating_good),
		EXCELLENT(5, R.string.rating_excellent);

		private int rating;
		private int nameResource;

		Rating(int rating, int nameResource) {
			this.rating = rating;
			this.nameResource = nameResource;
		}

		public static CharSequence[] getNames(Context context) {
			Rating[] values = values();
			String[] names = new String[values.length];
			int index = 0;
			for (Rating value : values) {
				names[index] = context.getString(value.nameResource);
				index++;
			}
			return names;
		}
	}

	private int selectedRating = 3;

	public SetRatingAlertDialog(Context context) {
		super(context);

		setTitle(R.string.set_rating_dialog_title);

		/*
			2 -- индекс для рейтинга "Средне" или 3 цифровом формате
		 */
		setSingleChoiceItems(Rating.getNames(context), 2, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				selectedRating = Rating.values()[which].rating;
			}
		});

		setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
	}

	public int getSelectedRating() {
		return selectedRating;
	}
}
