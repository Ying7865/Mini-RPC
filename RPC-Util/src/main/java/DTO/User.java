package DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    //Convert the raw network data into programmatically manipulable objects.
    private Integer userID;
    private String userName;
    private Integer userAge;
    private String userRole;

}
