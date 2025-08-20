package com.ceipa.GRHApp.Service;

import com.ceipa.GRHApp.Model.Conclusion;

import java.util.List;

public interface ConclusionService {

    List<Conclusion> getConlusionListByClassificationIds(List<Integer> classificationIds);

}
