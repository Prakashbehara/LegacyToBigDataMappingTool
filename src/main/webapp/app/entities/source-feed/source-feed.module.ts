import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared';
import {
    SourceFeedComponent,
    SourceFeedDetailComponent,
    SourceFeedUpdateComponent,
    SourceFeedDeletePopupComponent,
    SourceFeedDeleteDialogComponent,
    sourceFeedRoute,
    sourceFeedPopupRoute
} from './';

const ENTITY_STATES = [...sourceFeedRoute, ...sourceFeedPopupRoute];

@NgModule({
    imports: [MyAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SourceFeedComponent,
        SourceFeedDetailComponent,
        SourceFeedUpdateComponent,
        SourceFeedDeleteDialogComponent,
        SourceFeedDeletePopupComponent
    ],
    entryComponents: [SourceFeedComponent, SourceFeedUpdateComponent, SourceFeedDeleteDialogComponent, SourceFeedDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyAppSourceFeedModule {}
