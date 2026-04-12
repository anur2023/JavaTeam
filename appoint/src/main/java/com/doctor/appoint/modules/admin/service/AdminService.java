package com.doctor.appoint.modules.admin.service;
import com.doctor.appoint.modules.admin.dto.SpecialtyStatsDTO;
import com.doctor.appoint.modules.doctor.entity.DoctorSpecialty;
import com.doctor.appoint.modules.doctor.entity.Specialty;
import com.doctor.appoint.modules.doctor.repository.DoctorSpecialtyRepository;
import com.doctor.appoint.modules.doctor.repository.SpecialtyRepository;

import java.util.HashMap;
import java.util.Map;
import com.doctor.appoint.modules.admin.dto.AppointmentResponseDTO;
import com.doctor.appoint.modules.appointment.entity.Appointment;
import com.doctor.appoint.modules.appointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.doctor.appoint.modules.admin.dto.DailyReportDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.doctor.appoint.modules.admin.dto.RevenueReportDTO;
import com.doctor.appoint.modules.doctor.entity.Doctor;
import com.doctor.appoint.modules.doctor.repository.DoctorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorSpecialtyRepository doctorSpecialtyRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    public List<AppointmentResponseDTO> getAllAppointments() {

        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentResponseDTO> responseList = new ArrayList<>();

        for (Appointment appointment : appointments) {

            AppointmentResponseDTO dto = new AppointmentResponseDTO();
            dto.setAppointmentId(appointment.getId());
            dto.setPatientId(appointment.getPatientId());
            dto.setDoctorId(appointment.getDoctorId());
            dto.setSlotId(appointment.getSlot());
            dto.setMode(appointment.getMode());
            dto.setStatus(appointment.getStatus());
            dto.setBookingTime(appointment.getBookingTime());

            responseList.add(dto);
        }

        return responseList;
    }

    public DailyReportDTO getDailyReport() {

        List<Appointment> appointments = appointmentRepository.findAll();

        long total = 0;
        long online = 0;
        long offline = 0;

        LocalDate today = LocalDate.now();

        for (Appointment appointment : appointments) {

            LocalDate appointmentDate = appointment.getBookingTime().toLocalDate();

            if (appointmentDate.equals(today)) {

                total++;

                if ("ONLINE".equalsIgnoreCase(appointment.getMode())) {
                    online++;
                } else if ("OFFLINE".equalsIgnoreCase(appointment.getMode())) {
                    offline++;
                }
            }
        }

        DailyReportDTO report = new DailyReportDTO();
        report.setTotalAppointments(total);
        report.setOnlineAppointments(online);
        report.setOfflineAppointments(offline);

        return report;
    }
    public RevenueReportDTO getRevenueReport() {

        List<Appointment> appointments = appointmentRepository.findAll();

        double total = 0.0;
        double online = 0.0;
        double offline = 0.0;

        for (Appointment appointment : appointments) {

            Doctor doctor = doctorRepository.findById(appointment.getDoctorId()).orElse(null);

            if (doctor == null) {
                continue;
            }

            if ("ONLINE".equalsIgnoreCase(appointment.getMode())) {
                double fee = doctor.getConsultationFeeOnline() != null ? doctor.getConsultationFeeOnline() : 0.0;
                online += fee;
                total += fee;
            } else if ("OFFLINE".equalsIgnoreCase(appointment.getMode())) {
                double fee = doctor.getConsultationFeeOffline() != null ? doctor.getConsultationFeeOffline() : 0.0;
                offline += fee;
                total += fee;
            }
        }

        RevenueReportDTO report = new RevenueReportDTO();
        report.setTotalRevenue(total);
        report.setOnlineRevenue(online);
        report.setOfflineRevenue(offline);

        return report;
    }

    public List<SpecialtyStatsDTO> getSpecialtyStats() {

        List<Appointment> appointments = appointmentRepository.findAll();

        Map<Long, Long> specialtyCountMap = new HashMap<>();

        for (Appointment appointment : appointments) {

            Long doctorId = appointment.getDoctorId();

            List<DoctorSpecialty> mappings = doctorSpecialtyRepository.findByDoctorId(doctorId);

            for (DoctorSpecialty mapping : mappings) {

                Long specialtyId = mapping.getSpecialtyId();

                specialtyCountMap.put(
                        specialtyId,
                        specialtyCountMap.getOrDefault(specialtyId, 0L) + 1
                );
            }
        }

        List<SpecialtyStatsDTO> result = new ArrayList<>();

        for (Map.Entry<Long, Long> entry : specialtyCountMap.entrySet()) {

            Long specialtyId = entry.getKey();
            Long count = entry.getValue();

            Specialty specialty = specialtyRepository.findById(specialtyId).orElse(null);

            if (specialty != null) {

                SpecialtyStatsDTO dto = new SpecialtyStatsDTO();
                dto.setSpecialtyName(specialty.getName());
                dto.setAppointmentCount(count);

                result.add(dto);
            }
        }

        return result;
    }
}