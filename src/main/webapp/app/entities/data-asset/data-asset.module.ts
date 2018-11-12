import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyAppSharedModule } from 'app/shared';
import {
    DataAssetComponent,
    DataAssetDetailComponent,
    DataAssetUpdateComponent,
    DataAssetDeletePopupComponent,
    DataAssetDeleteDialogComponent,
    dataAssetRoute,
    dataAssetPopupRoute
} from './';

const ENTITY_STATES = [...dataAssetRoute, ...dataAssetPopupRoute];

@NgModule({
    imports: [MyAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DataAssetComponent,
        DataAssetDetailComponent,
        DataAssetUpdateComponent,
        DataAssetDeleteDialogComponent,
        DataAssetDeletePopupComponent
    ],
    entryComponents: [DataAssetComponent, DataAssetUpdateComponent, DataAssetDeleteDialogComponent, DataAssetDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyAppDataAssetModule {}
