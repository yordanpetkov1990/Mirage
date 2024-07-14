package nightclub.web.nightclub.entities.dtos;

import java.util.List;

public class EditReservationDTO {
    private Long id;
    private String status;
    private List<Long> tableIds;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Long> getTableIds() {
        return tableIds;
    }

    public void setTableIds(List<Long> tableIds) {
        this.tableIds = tableIds;
    }
}
