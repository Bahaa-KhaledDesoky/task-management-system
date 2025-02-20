package com.example.demo.Services;

import com.example.demo.Dtos.ActivityLogsDto;
import com.example.demo.Dtos.ProjectDto;
import com.example.demo.model.ActivityLogs;
import com.example.demo.model.AppUser;
import com.example.demo.model.Project;

import java.util.List;

public interface ActivityLogsService {
    public List<ActivityLogsDto> allLogs();
    public List<ActivityLogsDto> allLogsOfOwner(Integer logOwner_id);
    public void addLog(ActivityLogsDto activityLogsDto);
    public ActivityLogsDto GetLog(Integer id);
    public ActivityLogs GetRealLog(Integer id);
    public void updateLogs(Integer id,ActivityLogsDto activityLogsDto);
    public ActivityLogs mapLogToDto(ActivityLogsDto activityLogsDto);
    public ActivityLogsDto mapDtoToLog(ActivityLogs activityLogs);
}
