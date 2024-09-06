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

            if (StringUtils.hasLength(entityName)
                    && !entityName.toLowerCase().contains("entreprise")
                    && !entityName.toLowerCase().contains("roles")
                    && StringUtils.hasLength(idEntreprise)) {

                // Ajouter la condition WHERE pour filtrer par idEntreprise
                if (sql.contains("where")) {
                    sql = sql + " and " + entityName + ".identreprise = " + idEntreprise;
                } else {
                    sql = sql + " where " + entityName + ".identreprise = " + idEntreprise;
                }
            }
        }
        // Retourner la requête modifiée
        return sql;
    }
}
