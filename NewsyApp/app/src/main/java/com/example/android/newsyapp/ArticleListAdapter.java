package com.example.android.newsyapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by irosaro on 9/4/16.
 */
public class ArticleListAdapter extends ArrayAdapter<Article> {

    //custom article list adapter constructor
    public ArticleListAdapter(Activity context, ArrayList<Article> books) {
        super(context, 0, books);
    }

    // Components for the list item set up using getView customer adapter
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article item = getItem(position);

        //view reference
        View v = convertView;
        //view holder reference
        ViewHolder holder;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater.from(getContext()));
            v = inflater.inflate(R.layout.article_list_item, null);
            // cache the holder
            holder = new ViewHolder();
            holder.titleText = (TextView) v.findViewById(R.id.article_title);
            holder.sectionText = (TextView) v.findViewById(R.id.article_section);
            holder.authorText = (TextView) v.findViewById(R.id.author_article);
            holder.articleImage = (ImageView)v.findViewById(R.id.article_image);
            holder.arrowImage = (ImageView)v.findViewById(R.id.read_arrow);
            // associate  holder with view
            v.setTag(holder);
        } else {
            // if the view already exists, then get existing holder
            holder = (ViewHolder) v.getTag();
        }

        //set news title and section to view holder
        holder.titleText.setText(item.getArticleTitle());
        holder.sectionText.setText(item.getArticleSection());
        holder.authorText.setText(String.format("Author: %s", item.getArticleAuthor()));
        Picasso.with(getContext()).load(item.getArticleImage()).into(holder.articleImage);
        Picasso.with(getContext()).load(R.drawable.right).resize(50,50).into(holder.arrowImage);

        //return view holder view
        return v;
    }

    //ViewHolder class
    static class ViewHolder {
        TextView titleText;
        TextView sectionText;
        TextView authorText;
        ImageView articleImage;
        ImageView arrowImage;
    }
}