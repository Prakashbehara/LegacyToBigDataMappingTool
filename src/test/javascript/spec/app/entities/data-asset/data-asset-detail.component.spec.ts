/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { DataAssetDetailComponent } from 'app/entities/data-asset/data-asset-detail.component';
import { DataAsset } from 'app/shared/model/data-asset.model';

describe('Component Tests', () => {
    describe('DataAsset Management Detail Component', () => {
        let comp: DataAssetDetailComponent;
        let fixture: ComponentFixture<DataAssetDetailComponent>;
        const route = ({ data: of({ dataAsset: new DataAsset(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [DataAssetDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DataAssetDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataAssetDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dataAsset).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
