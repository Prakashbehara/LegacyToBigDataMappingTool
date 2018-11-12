import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared';
import {
    DataModelComponent,
    DataModelDetailComponent,
    DataModelUpdateComponent,
    DataModelDeletePopupComponent,
    DataModelDeleteDialogComponent,
    dataModelRoute,
    dataModelPopupRoute
} from './';

const ENTITY_STATES = [...dataModelRoute, ...dataModelPopupRoute];

@NgModule({
    imports: [MyAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DataModelComponent,
        DataModelDetailComponent,
        DataModelUpdateComponent,
        DataModelDeleteDialogComponent,
        DataModelDeletePopupComponent
    ],
    entryComponents: [DataModelComponent, DataModelUpdateComponent, DataModelDeleteDialogComponent, DataModelDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyAppDataModelModule {}
