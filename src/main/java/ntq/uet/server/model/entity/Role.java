package ntq.uet.server.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection = "roles")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    public enum ERole {
        ROLE_USER, ROLE_ADMIN
    }

    @Id
    private String id;

    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }

}
