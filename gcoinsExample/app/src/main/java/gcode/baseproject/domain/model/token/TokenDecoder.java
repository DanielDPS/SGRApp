package gcode.baseproject.domain.model.token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import androidx.annotation.NonNull;
import gcode.baseproject.domain.model.permissions.AccessLevel;
import gcode.baseproject.domain.model.account.Account;
import gcode.baseproject.domain.model.permissions.Module;
import gcode.baseproject.domain.model.permissions.ModuleParameters;
import gcode.baseproject.domain.model.permissions.Permission;
import gcode.baseproject.domain.model.permissions.VisitGuideModuleParameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class TokenDecoder {

    private TokenEncoder tokenEncoder;
    private String tokenKey;

    public TokenDecoder(String tokenKey, TokenEncoder tokenEncoder) {
        this.tokenKey = tokenKey;
        this.tokenEncoder = tokenEncoder;
    }

    private Jws<Claims> getClaims(String token) {
        Jws<Claims> claims =
                Jwts.parser()
                        .setSigningKey(tokenEncoder.encode(tokenKey))
                        .parseClaimsJws(token);
        return claims;
    }

    public Account parseAccount(String token) {

        Jws<Claims> claims = getClaims(token);

        String id = claims.getBody().get("id", String.class);
        String name = claims.getBody().get("nombre", String.class);
        String role = claims.getBody().get("rol", String.class);
        String image = claims.getBody().get("imagen", String.class);

        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setRole(role);
        account.setImage(image);

        return account;
    }

    public AccessLevel parseAccessLevelByModule(String token, Module module) {
        Jws<Claims> claims = getClaims(token);
        ArrayList<LinkedHashMap> maps = claims.getBody().get("permisos", ArrayList.class);
        for (LinkedHashMap map : maps) {
            String alias = (String) map.get("alias");
            int level = (int) map.get("nivel_acceso");
            if (module.getValue().equals(alias)) {
                AccessLevel accessLevel = AccessLevel.parse(level);
                return accessLevel;
            }
        }
        return null;
    }


    public HashMap<Module, AccessLevel> parseModulesAccessLevel(String token) {
        Jws<Claims> claims = getClaims(token);
        ArrayList<LinkedHashMap> maps = claims.getBody().get("permisos", ArrayList.class);
        HashMap<Module, AccessLevel> accessLevels = new HashMap<>();
        for (LinkedHashMap map : maps) {
            String alias = (String) map.get("alias");
            int level = (int) map.get("nivel_acceso");
            AccessLevel accessLevel = AccessLevel.parse(level);
            Module module = Module.parse(alias);
            accessLevels.put(module, accessLevel);
        }
        return accessLevels;
    }


    private LinkedHashMap getMapByModule(ArrayList<LinkedHashMap> maps, Module module) {
        for (LinkedHashMap map : maps) {
            String alias = (String) map.get("alias");
            if (alias.equals(module.getValue())) {
                return map;
            }
        }
        return null;
    }
    @NonNull
    public Permission parsePermissions(String token, Module module) {

        Permission permissions = new Permission();
        Jws<Claims> claims = getClaims(token);
        ArrayList<LinkedHashMap> maps = claims.getBody().get("permisos", ArrayList.class);
        LinkedHashMap moduleMap = getMapByModule(maps, module);
        LinkedHashMap actions = (LinkedHashMap) moduleMap.get("acciones");

        boolean isReadable = (boolean) actions.get("acceso");
        boolean isEditable = (boolean) actions.get("modificar");
        boolean isDeleteable = (boolean) actions.get("eliminar");
        boolean isAddable = (boolean) actions.get("crear");
        permissions.setReadable(isReadable);
        permissions.setEditable(isEditable);
        permissions.setRemoveable(isDeleteable);
        permissions.setAddable(isAddable);
        return permissions;
    }


    public ModuleParameters parseModule(String token, Module module) {
        ModuleParameters parameters = null;
        AccessLevel accessLevel = parseAccessLevelByModule(token, module);
        Permission permission = parsePermissions(token, module);
        switch (module) {

            case VisitGuide:
                VisitGuideModuleParameters visitGuideModuleParameters = new VisitGuideModuleParameters();
                visitGuideModuleParameters.setAccessLevel(accessLevel);
                visitGuideModuleParameters.setModule(module);
                visitGuideModuleParameters.setPermissions(permission);
                parameters = visitGuideModuleParameters;
                break;
        }
        return parameters;
    }
}
