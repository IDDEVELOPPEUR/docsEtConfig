package sn.edu.isep.dbe.docsEtConfig.entities.dto;
import lombok.*;
import sn.edu.isep.dbe.docsEtConfig.entities.Magasin;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MagasinDto {

    private Integer id;

    private String nom;

    private String adresse;

    private String telephone;

    private String description;

    private Integer createurId;

    public static MagasinDto fromMagasin(Magasin magasin) {
        return MagasinDto.builder()
                .id(magasin.getId())
                .nom(magasin.getNom())
                .adresse(magasin.getAdresse())
                .telephone(magasin.getTelephone())
                .description(magasin.getDescription())
                .createurId(magasin.getCreateur().getId())
                .build();
    }
}
