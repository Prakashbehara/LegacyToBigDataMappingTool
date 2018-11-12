import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared';
import {
    DataModelMappingComponent,
    DataModelMappingDetailComponent,
    DataModelMappingUpdateComponent,
    DataModelMappingDeletePopupComponent,
    DataModelMappingDeleteDialogComponent,
    dataModelMappingRoute,
    dataModelMappingPopupRoute
} from './';

const ENTITY_STATES = [...dataModelMappingRoute, ...dataModelMappingPopupRoute];

@NgModule({
    imports: [MyAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DataModelMappingComponent,
        DataModelMappingDetailComponent,
        DataModelMappingUpdateComponent,
        DataModelMappingDeleteDialogComponent,
        DataModelMappingDeletePopupComponent
    ],
    entryComponents: [
        DataModelMappingComponent,
        DataModelMappingUpdateComponent,
        DataModelMappingDeleteDialogComponent,
        DataModelMappingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyAppDataModelMappingModule {}
