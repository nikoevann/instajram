// Generated by view binder compiler. Do not edit!
package com.example.appstory.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.appstory.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemRowBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final ImageView imgprofile;

  @NonNull
  public final TextView tvJudul;

  private ItemRowBinding(@NonNull CardView rootView, @NonNull CardView cardView,
      @NonNull ImageView imgprofile, @NonNull TextView tvJudul) {
    this.rootView = rootView;
    this.cardView = cardView;
    this.imgprofile = imgprofile;
    this.tvJudul = tvJudul;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemRowBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_row, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemRowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      CardView cardView = (CardView) rootView;

      id = R.id.imgprofile;
      ImageView imgprofile = ViewBindings.findChildViewById(rootView, id);
      if (imgprofile == null) {
        break missingId;
      }

      id = R.id.tvJudul;
      TextView tvJudul = ViewBindings.findChildViewById(rootView, id);
      if (tvJudul == null) {
        break missingId;
      }

      return new ItemRowBinding((CardView) rootView, cardView, imgprofile, tvJudul);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}