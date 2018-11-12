import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MyAppApplicationModule } from './application/application.module';
import { MyAppFeedModule } from './feed/feed.module';
import { MyAppSourceMappingModule } from './source-mapping/source-mapping.module';
import { MyAppDataModelModule } from './data-model/data-model.module';
import { MyAppDataModelMappingModule } from './data-model-mapping/data-model-mapping.module';
import { MyAppDataAssetModule } from './data-asset/data-asset.module';
import { MyAppSourceFeedMappingModule } from './source-feed-mapping/source-feed-mapping.module';
import { MyAppSourceDatabaseMappingModule } from './source-database-mapping/source-database-mapping.module';
import { MyAppSourceFeedModule } from './source-feed/source-feed.module';
import { MyAppSourceDatabaseModule } from './source-database/source-database.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        MyAppApplicationModule,
        MyAppFeedModule,
        MyAppSourceMappingModule,        
        MyAppDataModelModule,
        MyAppDataModelMappingModule,
        MyAppDataAssetModule,
        MyAppSourceFeedMappingModule,
        MyAppSourceDatabaseMappingModule,
        MyAppSourceFeedModule,
        MyAppSourceDatabaseModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyAppEntityModule {}
