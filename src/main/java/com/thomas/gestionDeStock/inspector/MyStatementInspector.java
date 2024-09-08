package com.thomas.gestionDeStock.inspector;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

public class MyStatementInspector implements StatementInspector {

    @Override
    public String inspect(String sql) {
        if (StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")) {
            // Récupérer l'identifiant de l'entreprise à partir du MDC
            final String idEntreprise = MDC.get("idEntreprise");
            // Extraire le nom de l'entité
            final String entityName = sql.substring(7, sql.indexOf("."));

            // Vérifier que l'entité n'est pas entreprise ou roles, et qu'il y a un idEntreprise
            if (StringUtils.hasLength(entityName)
                    && !entityName.toLowerCase().contains("entreprise")  // exclure entreprise
                    && !sql.toLowerCase().contains("entreprise")         // exclure entreprise même avec alias
                    && !entityName.toLowerCase().contains("roles")       // exclure roles
                    && !sql.toLowerCase().contains("roles")              // exclure roles même avec alias
                    && !entityName.equalsIgnoreCase("Entreprise")  // Vérification stricte
                    && StringUtils.hasLength(idEntreprise)) {

                // Seulement ajouter identreprise si la colonne existe pour l'entité
                if (sql.contains("where")) {
                    sql = sql + " and " + entityName + ".identreprise = " + idEntreprise;
                } else {
                    sql = sql + " where " + entityName + ".identreprise = " + idEntreprise;
                }
            }
        }
        return sql;
    }

}
