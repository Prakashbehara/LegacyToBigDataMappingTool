/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyAppTestModule } from '../../../test.module';
import { DataAssetComponent } from 'app/entities/data-asset/data-asset.component';
import { DataAssetService } from 'app/entities/data-asset/data-asset.service';
import { DataAsset } from 'app/shared/model/data-asset.model';

describe('Component Tests', () => {
    describe('DataAsset Management Component', () => {
        let comp: DataAssetComponent;
        let fixture: ComponentFixture<DataAssetComponent>;
        let service: DataAssetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [DataAssetComponent],
                providers: []
            })
                .overrideTemplate(DataAssetComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DataAssetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataAssetService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DataAsset(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dataAssets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
