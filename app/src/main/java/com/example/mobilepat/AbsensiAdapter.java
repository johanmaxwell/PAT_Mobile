package com.example.mobilepat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilepat.models.HistoryResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AbsensiAdapter extends RecyclerView.Adapter<AbsensiAdapter.ViewHolder> {

    private List<HistoryResponse.LogAbsensi> absensiList;
    private SimpleDateFormat iso8601Formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    public AbsensiAdapter(List<HistoryResponse.LogAbsensi> absensiList) {
        this.absensiList = absensiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absensi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryResponse.LogAbsensi absensi = absensiList.get(position);

        try {
            // Parse the datetime strings
            Date waktuMasuk = iso8601Formatter.parse(absensi.getWaktu_masuk());
            Date waktuPulang = iso8601Formatter.parse(absensi.getWaktu_pulang());

            // Format date and time
            String date = dateFormatter.format(waktuMasuk);
            String timeMasuk = timeFormatter.format(waktuMasuk);
            String timePulang = timeFormatter.format(waktuPulang);

            // Bind data to views
            holder.dateText.setText("Tanggal: " + date);
            holder.waktuMasukText.setText("Jam Masuk: " + timeMasuk);
            holder.waktuPulangText.setText("Jam Pulang: " + timePulang);
            holder.statusText.setText("Status: " + absensi.getStatus());
        } catch (ParseException e) {
            e.printStackTrace();
            holder.dateText.setText("Invalid date");
            holder.waktuMasukText.setText("Invalid time");
            holder.waktuPulangText.setText("Invalid time");
            holder.statusText.setText("Status: " + absensi.getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return absensiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, waktuMasukText, waktuPulangText, statusText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateText);
            waktuMasukText = itemView.findViewById(R.id.waktuMasukText);
            waktuPulangText = itemView.findViewById(R.id.waktuPulangText);
            statusText = itemView.findViewById(R.id.statusText);
        }
    }
}
