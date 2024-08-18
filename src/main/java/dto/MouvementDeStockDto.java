package dto;

import com.thomas.gestionDeStock.model.MouvementDeStock;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class MouvementDeStockDto {

    private Integer id;

    private Instant dateMouvement;

    private BigDecimal quantite;

    private String typeMouvement;

    private ArticleDto article;

    // Entité vers Dto pour la récupération des données
    public static MouvementDeStockDto fromEntity(MouvementDeStock mouvementDeStock) {
        if (mouvementDeStock == null) {
            return null;
        }

        return MouvementDeStockDto.builder()
                .id(mouvementDeStock.getId())
                .dateMouvement(mouvementDeStock.getDateMouvement())
                .quantite(mouvementDeStock.getQuantite())
                .typeMouvement(mouvementDeStock.getTypeMouvement())
                .article(ArticleDto.fromEntity(mouvementDeStock.getArticle()))
                .build();
    }

    // Dto vers Entité pour la persistance des données
    public static MouvementDeStock toEntity(MouvementDeStockDto mouvementDeStockDto) {
        if (mouvementDeStockDto == null) {
            return null;
        }

        MouvementDeStock mouvementDeStock = new MouvementDeStock();
        mouvementDeStock.setId(mouvementDeStockDto.getId());
        mouvementDeStock.setDateMouvement(mouvementDeStockDto.getDateMouvement());
        mouvementDeStock.setQuantite(mouvementDeStockDto.getQuantite());
        mouvementDeStock.setTypeMouvement(mouvementDeStockDto.getTypeMouvement());
        mouvementDeStock.setArticle(ArticleDto.toEntity(mouvementDeStockDto.getArticle()));

        return mouvementDeStock;
    }
}
