package scu.miomin.com.keeper.patient.controller;

import scu.miomin.com.keeper.patient.adapter.ECGRecordAdapterForPatient;

/**
 * 描述:用户适配器 创建日期:2015/9/6
 *
 * @author 莫绪旻
 */
public class PatientController {

    public static ECGRecordAdapterForPatient ecgRecordAdapterForPatient;

    public static ECGRecordAdapterForPatient getEcgRecordAdapterForPatient() {
        return ecgRecordAdapterForPatient;
    }

    public static void setEcgRecordAdapterForPatient(ECGRecordAdapterForPatient ecgRecordAdapterForPatient) {
        PatientController.ecgRecordAdapterForPatient = ecgRecordAdapterForPatient;
    }

    // 释放资源
    public static void finish() {
        ecgRecordAdapterForPatient = null;
    }
}
