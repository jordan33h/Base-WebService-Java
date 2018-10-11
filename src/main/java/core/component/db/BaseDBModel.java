package core.component.db;

import lombok.Getter;
import java.util.Date;

@Getter
public class BaseDBModel
{
    //Unsign
    Long id;
    Date createAt, updateAt;
    //Tiny Int, Medium Int
    Short status;
}