package id.nyaa.exercise0420;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

class ListAdapter extends BaseAdapter implements Filterable {

    Context context;
    private ArrayList<Contact> contactsList;
    ArrayList<Contact> originalItems;


    public ListAdapter(
            ArrayList<Contact> list,
            Context context
    )
    {
        this.contactsList = list;
        this.context = context;
        originalItems = new ArrayList<>(contactsList);
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

    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            // Add the filter code here
            if (constraint != null && contactsList != null) {
                final ArrayList<Contact> nlist = new ArrayList<Contact>();
                String lowerConstraint = constraint.toString().toLowerCase();
                for (Contact contact : originalItems) {
                    final String value = contact.getNama().toLowerCase();

                    if (value.contains(lowerConstraint)) {
                        nlist.add(contact);
                    }
                }
                //following two lines is very important
                //as publish result can only take FilterResults users
                filterResults.values = nlist;
                filterResults.count = nlist.size();
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            contactsList = (ArrayList<Contact>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    private static class Holder {

        TextView ID_TextView;
        TextView Name_TextView;
        TextView PhoneNumberTextView;
    }

}