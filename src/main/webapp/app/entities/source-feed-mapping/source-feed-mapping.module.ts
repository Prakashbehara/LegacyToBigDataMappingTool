import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared';
import {
    SourceFeedMappingComponent,
    SourceFeedMappingDetailComponent,
    SourceFeedMappingUpdateComponent,
    SourceFeedMappingDeletePopupComponent,
    SourceFeedMappingDeleteDialogComponent,
    sourceFeedMappingRoute,
    sourceFeedMappingPopupRoute
} from './';

const ENTITY_STATES = [...sourceFeedMappingRoute, ...sourceFeedMappingPopupRoute];

@NgModule({
    imports: [MyAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SourceFeedMappingComponent,
        SourceFeedMappingDetailComponent,
        SourceFeedMappingUpdateComponent,
        SourceFeedMappingDeleteDialogComponent,
        SourceFeedMappingDeletePopupComponent
    ],
    entryComponents: [
        SourceFeedMappingComponent,
        SourceFeedMappingUpdateComponent,
        SourceFeedMappingDeleteDialogComponent,
        SourceFeedMappingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyAppSourceFeedMappingModule {}
