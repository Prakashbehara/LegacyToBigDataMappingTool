import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared';
import {
    SourceDatabaseMappingComponent,
    SourceDatabaseMappingDetailComponent,
    SourceDatabaseMappingUpdateComponent,
    SourceDatabaseMappingDeletePopupComponent,
    SourceDatabaseMappingDeleteDialogComponent,
    sourceDatabaseMappingRoute,
    sourceDatabaseMappingPopupRoute
} from './';

const ENTITY_STATES = [...sourceDatabaseMappingRoute, ...sourceDatabaseMappingPopupRoute];

@NgModule({
    imports: [MyAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SourceDatabaseMappingComponent,
        SourceDatabaseMappingDetailComponent,
        SourceDatabaseMappingUpdateComponent,
        SourceDatabaseMappingDeleteDialogComponent,
        SourceDatabaseMappingDeletePopupComponent
    ],
    entryComponents: [
        SourceDatabaseMappingComponent,
        SourceDatabaseMappingUpdateComponent,
        SourceDatabaseMappingDeleteDialogComponent,
        SourceDatabaseMappingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyAppSourceDatabaseMappingModule {}
