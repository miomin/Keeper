package scu.miomin.com.keeper.doctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import scu.miomin.com.keeper.Enum.AdministrativeEnum;
import scu.miomin.com.keeper.Enum.ProfessionalEnum;
import scu.miomin.com.keeper.Enum.SexEnum;
import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.activity.TreatmentFollowupActivity;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.bean.BirthdayBean;
import scu.miomin.com.keeper.bean.DoctorBean;
import scu.miomin.com.keeper.bean.HospitalBean;
import scu.miomin.com.keeper.bean.MyLocationBean;
import scu.miomin.com.keeper.bean.PatientBean;
import scu.miomin.com.keeper.bean.TreatmentBean;
import scu.miomin.com.keeper.doctor.adapter.TreatmentListAdapterForDoctor;

/**
 * 描述:病人端诊后随访界面 创建日期:2015/11/22
 *
 * @author 莫绪旻
 */
public class TreatmentListActivityForDoctor extends BaseActivity {

    private PullToRefreshListView lvTreatmentFollowupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_treatment_list_doctor);

        initView();
        initAdapter();
        setListener();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TreatmentListActivityForDoctor.class);
        context.startActivity(intent);
    }

    private void initView() {
        lvTreatmentFollowupList = (PullToRefreshListView) findViewById(R.id.lvTreatment);
    }

    private void initAdapter() {
        // 创建适配器对象
        TreatmentListAdapterForDoctor treatmentFollowupAdapterForPatient = new TreatmentListAdapterForDoctor(this);
        // 将ListView与适配器关联
        lvTreatmentFollowupList.setAdapter(treatmentFollowupAdapterForPatient);

        TreatmentBean treatmentFollowupForPatientBean;

        treatmentFollowupForPatientBean = new TreatmentBean("2015-11-24",
                new DoctorBean("2013141463002", "123456", "莫医生", SexEnum.MAN,
                        new BirthdayBean(1966, 1, 1), null, AdministrativeEnum.NEIKE,
                        new HospitalBean("桂林医学院", "广西", "桂林", "叠彩区",
                                new MyLocationBean(0, 0)), ProfessionalEnum.ZHURENYISHI,
                        "广西中医协会常委，桂林医学院心内科专家，有丰富的心脏病临床治疗经验", true),
                new PatientBean("18084803926", "123456", "莫绪旻", SexEnum.MAN,
                        new BirthdayBean(1993, 8, 15), null, 171, 57),
                "心律不齐，脉搏紊乱，生活作息不规律");
        treatmentFollowupAdapterForPatient.add(treatmentFollowupForPatientBean);

        treatmentFollowupForPatientBean = new TreatmentBean("2015-11-19",
                new DoctorBean("2013141463040", "123456", "王鹏", SexEnum.MAN,
                        new BirthdayBean(1987, 4, 19), null, AdministrativeEnum.NEIKE,
                        new HospitalBean("四川大学华西医院", "四川", "成都", "锦江区",
                                new MyLocationBean(0, 0)), ProfessionalEnum.ZHUZHIYISHI,
                        "资深内科医生，临床经验丰富，擅长治疗冠心病、心律不齐", true),
                new PatientBean("18084803926", "123456", "莫绪旻", SexEnum.MAN,
                        new BirthdayBean(1993, 8, 15), null, 171, 57),
                "窦性心律不齐，熬夜导致");
        treatmentFollowupAdapterForPatient.add(treatmentFollowupForPatientBean);

        treatmentFollowupForPatientBean = new TreatmentBean("2015-11-15",
                new DoctorBean("2013141463002", "123456", "莫医生", SexEnum.MAN,
                        new BirthdayBean(1966, 1, 1), null, AdministrativeEnum.NEIKE,
                        new HospitalBean("桂林医学院", "广西", "桂林", "叠彩区",
                                new MyLocationBean(0, 0)), ProfessionalEnum.ZHURENYISHI,
                        "广西中医协会常委，桂林医学院心内科专家，有丰富的心脏病临床治疗经验", true),
                new PatientBean("18000000000", "123456", "刘康龙", SexEnum.MAN,
                        new BirthdayBean(1996, 1, 15), null, 193, 87),
                "偶发性早搏，历史遗留问题");
        treatmentFollowupAdapterForPatient.add(treatmentFollowupForPatientBean);

        treatmentFollowupForPatientBean = new TreatmentBean("2015-11-11",
                new DoctorBean("2013141463002", "123456", "莫医生", SexEnum.MAN,
                        new BirthdayBean(1966, 1, 1), null, AdministrativeEnum.NEIKE,
                        new HospitalBean("桂林医学院", "广西", "桂林", "叠彩区",
                                new MyLocationBean(0, 0)), ProfessionalEnum.ZHURENYISHI,
                        "广西中医协会常委，桂林医学院心内科专家，有丰富的心脏病临床治疗经验", true),
                new PatientBean("18000000001", "123456", "应俊康", SexEnum.MAN,
                        new BirthdayBean(1995, 1, 15), null, 182, 77),
                "心动过速，遗传病，剧烈运动后更为明显");
        treatmentFollowupAdapterForPatient.add(treatmentFollowupForPatientBean);

        treatmentFollowupForPatientBean = new TreatmentBean("2015-11-8",
                new DoctorBean("2013141463040", "123456", "王鹏", SexEnum.MAN,
                        new BirthdayBean(1987, 4, 19), null, AdministrativeEnum.NEIKE,
                        new HospitalBean("四川大学华西医院", "四川", "成都", "锦江区",
                                new MyLocationBean(0, 0)), ProfessionalEnum.ZHUZHIYISHI,
                        "资深内科医生，临床经验丰富，擅长治疗冠心病、心律不齐", true),
                new PatientBean("18000000000", "123456", "刘康龙", SexEnum.MAN,
                        new BirthdayBean(1996, 1, 15), null, 193, 87),
                "偶发性早搏，历史遗留问题");
        treatmentFollowupAdapterForPatient.add(treatmentFollowupForPatientBean);

        treatmentFollowupForPatientBean = new TreatmentBean("2015-11-6",
                new DoctorBean("2013141463001", "123456", "陈钊", SexEnum.MAN,
                        new BirthdayBean(1995, 1, 1), null, AdministrativeEnum.ERKE,
                        new HospitalBean("四川大学华西医院", "四川", "成都", "锦江区",
                                new MyLocationBean(0, 0)), ProfessionalEnum.ZHUZHIYISHI,
                        "儿科专家，擅长青少年心脏病的预防和治疗，四川大学华西医学院特聘教授", false),
                new PatientBean("18000000000", "123456", "刘康龙", SexEnum.MAN,
                        new BirthdayBean(1996, 1, 15), null, 193, 87),
                "偶发性早搏，历史遗留问题");
        treatmentFollowupAdapterForPatient.add(treatmentFollowupForPatientBean);

        treatmentFollowupForPatientBean = new TreatmentBean("2015-11-4",
                new DoctorBean("2013141463001", "123456", "陈钊", SexEnum.MAN,
                        new BirthdayBean(1995, 1, 1), null, AdministrativeEnum.ERKE,
                        new HospitalBean("四川大学华西医院", "四川", "成都", "锦江区",
                                new MyLocationBean(0, 0)), ProfessionalEnum.ZHUZHIYISHI,
                        "儿科专家，擅长青少年心脏病的预防和治疗，四川大学华西医学院特聘教授", false),
                new PatientBean("18084803926", "123456", "莫绪旻", SexEnum.MAN,
                        new BirthdayBean(1993, 8, 15), null, 171, 57),
                "心律不齐，脉搏紊乱，生活作息不规律");
        treatmentFollowupAdapterForPatient.add(treatmentFollowupForPatientBean);
    }

    private void setListener() {
        // 设置下拉刷新监听器
        lvTreatmentFollowupList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, Void>() {
                    // 异步加载数据
                    @Override
                    protected Void doInBackground(Void... arg0) {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    // 修改UI
                    protected void onPostExecute(Void result) {
                        lvTreatmentFollowupList.onRefreshComplete();
                    }
                }.execute();
            }
        });

        lvTreatmentFollowupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TreatmentFollowupActivity.actionStart(TreatmentListActivityForDoctor.this);
            }
        });
    }

    public void back(View view) {
        finish();
    }
}
