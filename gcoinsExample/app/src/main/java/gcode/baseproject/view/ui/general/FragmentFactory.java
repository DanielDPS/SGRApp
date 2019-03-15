package gcode.baseproject.view.ui.general;

import gcode.baseproject.domain.model.permissions.AccessLevel;
import gcode.baseproject.domain.model.permissions.Module;
import gcode.baseproject.domain.model.permissions.ModuleParameters;
import gcode.baseproject.domain.model.permissions.VisitGuideModuleParameters;

public class FragmentFactory {

    public BaseFragment createFragment(ModuleParameters moduleParameters) {

        BaseFragment fragment = null;

        Module module = moduleParameters.getModule();

        switch (module) {
            case VisitGuide:
                break;
            case Orders:
                break;
            case Deposits:
                break;
        }
        return fragment;
    }


    private boolean isSuperAccess(AccessLevel accessLevel) {
        return accessLevel == AccessLevel.SuperAccess;
    }

    private BaseFragment buildVisitGuideFragment(ModuleParameters parameters) {
        BaseFragment fragment;
        VisitGuideModuleParameters guideModuleParameters = (VisitGuideModuleParameters)parameters;
        if (isSuperAccess(parameters.getAccessLevel())) {
            //fragment = VisitGuideSellersFragment.newInstance(guideModuleParameters.getPermissions());
        } else {
            fragment = null;
        }
        return null;
    }

    private BaseFragment buildDepositsFragment(AccessLevel accessLevel) {
        return null;
    }

    private BaseFragment buildOrdersFragment(AccessLevel accessLevel) {
        return null;
    }

    private BaseFragment buildCutsFragment(AccessLevel accessLevel) {
        return null;
    }
}