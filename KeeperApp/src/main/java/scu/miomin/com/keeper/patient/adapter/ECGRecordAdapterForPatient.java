package scu.miomin.com.keeper.patient.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.basedialog.BaseDialog;
import scu.miomin.com.keeper.bean.ECGRecordBean;

/**
 * 描述:对话列表适配器 创建日期:2015/11/10
 *
 * @author 莫绪旻
 */
public class ECGRecordAdapterForPatient extends BaseAdapter {

    private ArrayList<ECGRecordBean> listECGRecord;
    private Context context;

    // 构造器
    public ECGRecordAdapterForPatient(Context context) {
        super();
        this.listECGRecord = new ArrayList<ECGRecordBean>();
        this.context = context;
        notifyDataSetChanged();
    }

    public void pushSucceed(int position) {
        listECGRecord.get(position).setisLocality(true);
        notifyDataSetChanged();
    }

    // 添加一条记录
    public void add(ECGRecordBean ecgRecord) {

        listECGRecord.add(ecgRecord);
        notifyDataSetChanged();
    }

    // 删除一条记录
    public void delete(int index) {

        listECGRecord.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listECGRecord.size();
    }

    @Override
    public Object getItem(int position) {
        return listECGRecord.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 保留一个item的控件
        viewHolder holder = null;

        if (convertView == null) {
            // 拿到ListViewItem的布局（一行，需要单独定义一个），转换为View类型的对象
            convertView = View.inflate(context, R.layout.item_ecg_record, null);
            holder = new viewHolder();
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.ivState = (ImageView) convertView.findViewById(R.id.ivState);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }

        holder.ivState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDialog.actionStartActivity(1, position, (Activity) context,
                        "提示", "是否要将本次心电监测数据上传到云端？上传后可供医生查看",
                        "否", "是");
            }
        });

        // 更新保留的控件中的数据
        ECGRecordBean ecgRecord = listECGRecord.get(position);


        holder.tvDate.setText(ecgRecord.getDate());

        if (ecgRecord.isLocality())
            holder.ivState.setImageResource(R.drawable.socialize_pocket);
        else
            holder.ivState.setImageResource(R.drawable.socialize_pocket_gray);
        // 将更新后的控件返回给Android系统
        return convertView;
    }

    class viewHolder {
        TextView tvDate;
        ImageView ivState;
    }
}