import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared';
import {
    SourceDatabaseComponent,
    SourceDatabaseDetailComponent,
    SourceDatabaseUpdateComponent,
    SourceDatabaseDeletePopupComponent,
    SourceDatabaseDeleteDialogComponent,
    sourceDatabaseRoute,
    sourceDatabasePopupRoute
} from './';

const ENTITY_STATES = [...sourceDatabaseRoute, ...sourceDatabasePopupRoute];

@NgModule({
    imports: [MyAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SourceDatabaseComponent,
        SourceDatabaseDetailComponent,
        SourceDatabaseUpdateComponent,
        SourceDatabaseDeleteDialogComponent,
        SourceDatabaseDeletePopupComponent
    ],
    entryComponents: [
        SourceDatabaseComponent,
        SourceDatabaseUpdateComponent,
        SourceDatabaseDeleteDialogComponent,
        SourceDatabaseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyAppSourceDatabaseModule {}
