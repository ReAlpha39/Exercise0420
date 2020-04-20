package id.nyaa.exercise0420;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

    Context context;
    private ArrayList<Contact> contactsList;


    public ListAdapter(
            ArrayList<Contact> list,
            Context context
    )
    {
        this.contactsList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return contactsList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return contactsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.items, null);

            holder = new Holder();

            holder.Name_TextView = (TextView) convertView.findViewById(R.id.textViewNAME);
            holder.PhoneNumberTextView = (TextView) convertView.findViewById(R.id.textViewPHONE_NUMBER);

            convertView.setTag(holder);

        } else {

            holder = (Holder) convertView.getTag();
        }

        Contact contact = contactsList.get(position);
        holder.Name_TextView.setText(contact.getNama());
        holder.PhoneNumberTextView.setText(contact.getNomor());

        return convertView;
    }

    private static class Holder {

        TextView ID_TextView;
        TextView Name_TextView;
        TextView PhoneNumberTextView;
    }

}
