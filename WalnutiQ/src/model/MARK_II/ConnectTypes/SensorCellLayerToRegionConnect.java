package model.MARK_II.ConnectTypes;

import model.MARK_II.Region;
import model.MARK_II.SensorCellLayer;

public interface SensorCellLayerToRegionConnect {
    public abstract void connect(SensorCellLayer sensorCellLayer, Region region,
	    int numberOfColumnsToOverlapAlongXAxisOfSensorCellLayer,
	    int numberOfColumnsToOverlapAlongYAxisOfSensorCellLayer);

}
