package com.company.persacc.web.screens.staff;

import com.haulmont.cuba.gui.screen.*;
import com.company.persacc.entity.Staff;

@UiController("persacc_Staff.edit")
@UiDescriptor("staff-edit.xml")
@EditedEntityContainer("staffDc")
@LoadDataBeforeShow
public class StaffEdit extends StandardEditor<Staff> {
}