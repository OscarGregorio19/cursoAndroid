package goyo19.example.com.fruitworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import goyo19.example.com.fruitworld.R;
import goyo19.example.com.fruitworld.models.Fruit;

public class FruitAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Fruit> frutas;

    public FruitAdapter(Context context, int layout, List<Fruit> frutas) {
        this.context = context;
        this.layout = layout;
        this.frutas = frutas;
    }

    @Override
    public int getCount() {
        return frutas.size();
    }

    @Override
    public Object getItem(int position) {
        return frutas.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //view holder
        ViewHolder holder;

        if(convertView == null) {
            // Sólo si está nulo, es decir, primera vez en ser renderizado, inflamos
            // y adjuntamos las referencias del layout en una nueva instancia de nuestro
            // ViewHolder, y lo insertamos dentro del convertView, para reciclar su uso
            convertView = LayoutInflater.from(context).inflate(layout, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.textViewName);
            holder.origin = convertView.findViewById(R.id.textViewOrigin);
            holder.icon = convertView.findViewById(R.id.imageViewIcon);
            convertView.setTag(holder);
        } else {
            // Obtenemos la referencia que posteriormente pusimos dentro del convertView
            // Y así, reciclamos su uso sin necesidad de buscar de nuevo, referencias con FindViewById
            holder = (ViewHolder) convertView.getTag();
        }

        final Fruit currentFruit = (Fruit) getItem(position);
        holder.name.setText(currentFruit.getNombre());
        holder.origin.setText(currentFruit.getOrigin());
        holder.icon.setImageResource(currentFruit.getIcon());

        return convertView;
    }

    static class ViewHolder {
        private TextView name;
        private TextView origin;
        private ImageView icon;
    }
}
