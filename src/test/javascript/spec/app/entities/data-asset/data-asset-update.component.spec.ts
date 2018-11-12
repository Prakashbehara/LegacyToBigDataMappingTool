/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { DataAssetUpdateComponent } from 'app/entities/data-asset/data-asset-update.component';
import { DataAssetService } from 'app/entities/data-asset/data-asset.service';
import { DataAsset } from 'app/shared/model/data-asset.model';

describe('Component Tests', () => {
    describe('DataAsset Management Update Component', () => {
        let comp: DataAssetUpdateComponent;
        let fixture: ComponentFixture<DataAssetUpdateComponent>;
        let service: DataAssetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [DataAssetUpdateComponent]
            })
                .overrideTemplate(DataAssetUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DataAssetUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataAssetService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataAsset(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataAsset = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataAsset();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataAsset = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
