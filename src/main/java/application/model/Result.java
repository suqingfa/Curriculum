package application.model;

import lombok.Data;

@Data
public class Result<T>
{
    private String result;
    private String msg;
    private T object;
}
