package scu.miomin.com.keeper.patient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.bean.DoctorBean;
import scu.miomin.com.keeper.myview.MyBanner;
import scu.miomin.com.keeper.resource.MyLoader;

/**
 * Created by miomin on 15/11/22.
 */
public class RemenDoctorAdapter extends BaseAdapter {

    private ArrayList<DoctorBean> remenDoctorList;
    private Context context;
    private MyBanner myBanner;

    public RemenDoctorAdapter(ArrayList<DoctorBean> remenDoctorList, Context context) {
        this.context = context;
        this.remenDoctorList = remenDoctorList;
    }

    public void add(DoctorBean doctorBean) {
        remenDoctorList.add(doctorBean);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return remenDoctorList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return remenDoctorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (position == 0) {
            convertView = View.inflate(context, R.layout.layout_geren_top, null);
            myBanner = (MyBanner) convertView.findViewById(R.id.myBanner);
            myBanner.startPlay();
        } else {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_remendoctor, null);
                viewHolder = new ViewHolder();
                viewHolder.ivHead = (ImageView) convertView.findViewById(R.id.ivHead);
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                viewHolder.tvAdministrative = (TextView) convertView.findViewById(R.id.tvAdministrative);
                viewHolder.tvProfessional = (TextView) convertView.findViewById(R.id.tvProfessional);
                viewHolder.tvHospital = (TextView) convertView.findViewById(R.id.tvHospital);
                viewHolder.tvIntroduction = (TextView) convertView.findViewById(R.id.tvIntroduction);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            DoctorBean doctorBean = remenDoctorList.get(position - 1);

            viewHolder.tvName.setText(doctorBean.getName());
            viewHolder.tvAdministrative.setText(doctorBean.getAdministrative());
            viewHolder.tvProfessional.setText(doctorBean.getProfessional());
            viewHolder.tvHospital.setText(doctorBean.getHospitalBean().getName());
            viewHolder.tvIntroduction.setText(doctorBean.getIntroduction());

            MyLoader.dispalyFromAssets(doctorBean.getHeadUrl(),
                    viewHolder.ivHead);
        }
        return convertView;
    }

    class ViewHolder {
        public ImageView ivHead;
        public TextView tvName;
        public TextView tvAdministrative;
        public TextView tvProfessional;
        public TextView tvHospital;
        public TextView tvIntroduction;
    }

    public void stopBanner() {
        myBanner.stopPlay();
    }
}
