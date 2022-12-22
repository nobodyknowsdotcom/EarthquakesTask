package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
public class Earthquake {
    private String id;
    private int depth;
    private String type;
    private float magnitude;
    private String state;
    private int year;
}
