package cz.mendelu.xotradov;

import hudson.security.Permission;
import jenkins.model.Jenkins;

class PermissionHandler {
    static final Permission SIMPLE_QUEUE_MOVE_PERMISSION = Jenkins.ADMINISTER;
    static final Permission SIMPLE_QUEUE_RESET_PERMISSION = Jenkins.ADMINISTER;
}
