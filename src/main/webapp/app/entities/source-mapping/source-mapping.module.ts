import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared';
import {
    SourceMappingComponent,
    SourceMappingDetailComponent,
    SourceMappingUpdateComponent,
    SourceMappingDeletePopupComponent,
    SourceMappingDeleteDialogComponent,
    sourceMappingRoute,
    sourceMappingPopupRoute
} from './';

const ENTITY_STATES = [...sourceMappingRoute, ...sourceMappingPopupRoute];

@NgModule({
    imports: [MyAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SourceMappingComponent,
        SourceMappingDetailComponent,
        SourceMappingUpdateComponent,
        SourceMappingDeleteDialogComponent,
        SourceMappingDeletePopupComponent
    ],
    entryComponents: [
        SourceMappingComponent,
        SourceMappingUpdateComponent,
        SourceMappingDeleteDialogComponent,
        SourceMappingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyAppSourceMappingModule {}
