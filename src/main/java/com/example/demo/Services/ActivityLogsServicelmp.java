package com.example.demo.Services;

import com.example.demo.Dtos.ActivityLogsDto;
import com.example.demo.Repository.ActivityLogsRepository;
import com.example.demo.model.ActivityLogs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityLogsServicelmp implements ActivityLogsService{
    private final ActivityLogsRepository activityLogsRepository;
    private final UserServiceImp userServiceImp;
    @Override
    public List<ActivityLogsDto> allLogs() {
        return activityLogsRepository.findAll().stream().map(log->mapDtoToLog(log)).collect(Collectors.toList());
    }

    @Override
    public List<ActivityLogsDto> allLogsOfOwner(Integer logOwner_id) {
        return activityLogsRepository.findLogsOfUser(logOwner_id).stream().map(log->mapDtoToLog(log)).collect(Collectors.toList());
    }

    @Override
    public void addLog(ActivityLogsDto activityLogsDto) {
        ActivityLogs activityLogs=mapLogToDto(activityLogsDto);
        activityLogsRepository.save(activityLogs);
    }

    @Override
    public ActivityLogsDto GetLog(Integer id) {
        ActivityLogs activityLogs=activityLogsRepository.findById(id).get();
        return mapDtoToLog(activityLogs);
    }

    @Override
    public ActivityLogs GetRealLog(Integer id) {
        return activityLogsRepository.findById(id).get();
    }

    @Override
    public void updateLogs(Integer id, ActivityLogsDto activityLogsDto) {
        ActivityLogs activityLogs=mapLogToDto(activityLogsDto);
        activityLogs.setId(id);
        activityLogsRepository.save(activityLogs);
    }

    @Override
    public ActivityLogs mapLogToDto(ActivityLogsDto activityLogsDto) {

        return ActivityLogs.builder()
                .logOwner(userServiceImp.findUserById(activityLogsDto.getLogOwner()).get())
                .action(activityLogsDto.getAction())
                .createdAt(activityLogsDto.getCreatedAt())
                .entity(activityLogsDto.getEntity())
                .idEntity(activityLogsDto.getIdEntity())
                .build();
    }

    @Override
    public ActivityLogsDto mapDtoToLog(ActivityLogs activityLogs) {
        return ActivityLogsDto.builder()
                .logOwner(activityLogs.getLogOwner().getId())
                .action(activityLogs.getAction())
                .createdAt(activityLogs.getCreatedAt())
                .entity(activityLogs.getEntity())
                .idEntity(activityLogs.getIdEntity())
                .build();
    }
}
