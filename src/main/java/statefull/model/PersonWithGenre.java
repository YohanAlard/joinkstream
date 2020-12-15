package statefull.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonWithGenre {
    String nom;
    String prenom;
    RefGenre codeGenre;
}
