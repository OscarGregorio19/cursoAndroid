package goyo19.example.com.demo2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<String> names;

    public MyAdapter(Context context, int layout, List<String> names) {
        this.context = context;
        this.layout = layout;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        //view holder pattern
        ViewHolder holder;


        if(convertView == null) {
            //inflamos la vista que nos ha llegado con  uestro valor personalizado
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(this.layout, null);

            holder = new ViewHolder();
            //refernciamos el elemento y lo rellenamos
            holder.nameTextView = convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //nos traemos el valor dependiente de la posicion
        String currentName = names.get(position);
        //currentName = (String) getItem(position);

        //refernciamos el elemento y lo rellenamos
        holder.nameTextView.setText(currentName);

        //devolvemos la vista inflada y modificada nuestros datos
        return convertView;
    }

    static class ViewHolder {
        private TextView nameTextView;
    }
}
