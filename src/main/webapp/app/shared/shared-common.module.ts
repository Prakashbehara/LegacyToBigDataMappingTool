import { NgModule } from '@angular/core';

import { MyAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [MyAppSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [MyAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MyAppSharedCommonModule {}
